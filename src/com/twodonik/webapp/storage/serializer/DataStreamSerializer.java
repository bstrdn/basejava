package com.twodonik.webapp.storage.serializer;

import com.twodonik.webapp.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.twodonik.webapp.model.SectionType.*;

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
            dos.writeUTF(((TextSection) section.get(OBJECTIVE)).getContent());
            dos.writeUTF(((TextSection) section.get(PERSONAL)).getContent());
            writeListSection(dos, section, ACHIEVEMENT);
            writeListSection(dos, section, QUALIFICATION);
            writeOrganizationSection(dos, section, EXPERIENCE);
            writeOrganizationSection(dos, section, EDUCATION);
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
            resume.addSection(OBJECTIVE, new TextSection(dis.readUTF()));
            resume.addSection(PERSONAL, new TextSection(dis.readUTF()));
            resume.addSection(ACHIEVEMENT, readListSection(dis));
            resume.addSection(QUALIFICATION, readListSection(dis));
            resume.addSection(EXPERIENCE, readOrganizationSection(dis));
            resume.addSection(EDUCATION, readOrganizationSection(dis));
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
            dos.writeUTF(link.getUrl());
            List<Position> lp = organization.getPositions();
            dos.writeInt(lp.size());
            for (Position position : lp) {
                dos.writeInt(position.getStartDate().getYear());
                dos.writeInt(position.getStartDate().getMonthValue());
                dos.writeInt(position.getEndDate().getYear());
                dos.writeInt(position.getEndDate().getMonthValue());
                dos.writeUTF(position.getTitle());
                dos.writeUTF(position.getDescription());
            }
        }
    }
}
