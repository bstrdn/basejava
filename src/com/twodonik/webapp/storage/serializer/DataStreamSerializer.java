package com.twodonik.webapp.storage.serializer;

import com.twodonik.webapp.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactType, String> contact = resume.getContact();
            dos.writeInt(contact.size());
            for (Map.Entry<ContactType, String> entry : contact.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, AbstractSection> section = resume.getSection();
            dos.writeInt(section.size());
            for (Map.Entry<SectionType, AbstractSection> m : section.entrySet()) {
                SectionType st = m.getKey();
                dos.writeUTF(st.name());
                switch (st) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(((TextSection) section.get(st)).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        writeListSection(dos, section, st);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeOrganizationSection(dos, section, st);
                        break;
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            //contact
            int cont = dis.readInt();
            for (int i = 0; i < cont; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            //section
            int count = dis.readInt();
            for (int i = 0; i < count; i++) {
                SectionType st = SectionType.valueOf(dis.readUTF());
                switch (st) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.addSection(st, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        resume.addSection(st, readListSection(dis));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        resume.addSection(st, readOrganizationSection(dis));
                        break;
                }
            }
            return resume;
        }
    }

    private AbstractSection readListSection(DataInputStream dis) throws IOException {
        int achSize = dis.readInt();
        ArrayList<String> ach = new ArrayList<>(achSize);
        for (int i = 0; i < achSize; i++) {
            ach.add(dis.readUTF());
        }
        return new ListSection(ach);
    }

    private AbstractSection readOrganizationSection(DataInputStream dis) throws IOException {
        int loSize = dis.readInt();
        List<Organization> lo = new ArrayList<>();
        for (int i = 0; i < loSize; i++) {
            Link link = new Link(dis.readUTF(), dis.readUTF());
            int lpSize = dis.readInt();
            List<Position> lp = new ArrayList<>();
            for (int j = 0; j < lpSize; j++) {
                lp.add(new Position(YearMonth.of(dis.readInt(), dis.readInt()), YearMonth.of(dis.readInt(), dis.readInt()), dis.readUTF(), dis.readUTF()));
            }
            lo.add(new Organization(link, lp));
        }
        return new OrganizationSection(lo);
    }

    private void writeListSection(DataOutputStream dos, Map<SectionType, AbstractSection> section, SectionType sectionType) throws IOException {
        List<String> ach = ((ListSection) section.get(sectionType)).getItems();
        int size = ach.size();
        dos.writeInt(size);
        for (int i = 0; i < size; i++) {
            dos.writeUTF(ach.get(i));
        }
    }

    private void writeOrganizationSection(DataOutputStream dos, Map<SectionType, AbstractSection> section, SectionType sectionType) throws IOException {
        List<Organization> lr = ((OrganizationSection) section.get(sectionType)).getOrganizations();
        int org = lr.size();
        dos.writeInt(org);
        for (Organization organization : lr) {
            Link link = organization.getLink();
            dos.writeUTF(link.getName());
            dos.writeUTF(link.getUrl() == null ? "" : link.getUrl());
            List<Position> lp = organization.getPositions();
            dos.writeInt(lp.size());
            for (Position position : lp) {
                writeDate(dos, position);
                dos.writeUTF(position.getTitle());
                dos.writeUTF(position.getDescription());
            }
        }
    }

    private void writeDate(DataOutputStream dos, Position position) throws IOException {
        dos.writeInt(position.getStartDate().getYear());
        dos.writeInt(position.getStartDate().getMonthValue());
        dos.writeInt(position.getEndDate().getYear());
        dos.writeInt(position.getEndDate().getMonthValue());
    }
}

