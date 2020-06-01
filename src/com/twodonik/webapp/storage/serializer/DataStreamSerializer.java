package com.twodonik.webapp.storage.serializer;

import com.twodonik.webapp.exception.StorageException;
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

            contact.forEach((c, s) -> {
                try {
                    dos.writeUTF(c.name());
                    dos.writeUTF(s);
                } catch (IOException e) {
                    throw new StorageException("IO Exception", e);
                }
            });

            Map<SectionType, AbstractSection> section = resume.getSection();
            dos.writeInt(section.size());

            section.forEach((sectionType, abstractSection) -> {
                try {
                    dos.writeUTF(sectionType.name());
                    switch (sectionType) {
                        case OBJECTIVE:
                        case PERSONAL:
                            dos.writeUTF(((TextSection) abstractSection).getContent());
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATION:
                            writeListSection(dos, abstractSection);
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            writeOrganizationSection(dos, abstractSection);
                            break;
                    }
                } catch (IOException e) {
                    throw new StorageException("IOExceprion", e);
                }
            });
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
            String url = dis.readUTF();
            Link link = new Link(dis.readUTF(), url.equals("") ? null : url);
            int lpSize = dis.readInt();
            List<Position> lp = new ArrayList<>();
            for (int j = 0; j < lpSize; j++) {
                lp.add(new Position(readDate(dis.readInt(), dis.readInt()), readDate(dis.readInt(), dis.readInt()), dis.readUTF(), dis.readUTF()));
            }
            lo.add(new Organization(link, lp));
        }
        return new OrganizationSection(lo);
    }

    private void writeListSection(DataOutputStream dos, AbstractSection section) throws IOException {
        List<String> ach = ((ListSection) section).getItems();
        int size = ach.size();
        dos.writeInt(size);

        for (int i = 0; i < size; i++) {
            dos.writeUTF(ach.get(i));
        }
    }

    private void writeOrganizationSection(DataOutputStream dos, AbstractSection section) throws IOException {
        List<Organization> lr = ((OrganizationSection) section).getOrganizations();
        int org = lr.size();
        dos.writeInt(org);
        for (Organization organization : lr) {
            Link link = organization.getLink();
            dos.writeUTF(link.getUrl() == null ? "" : link.getUrl());
            dos.writeUTF(link.getName());
            List<Position> lp = organization.getPositions();
            dos.writeInt(lp.size());
            for (Position position : lp) {
                writeDate(dos, position.getStartDate());
                writeDate(dos, position.getEndDate());
                dos.writeUTF(position.getTitle());
                dos.writeUTF(position.getDescription() == null ? "" : position.getDescription());
            }
        }
    }

    private YearMonth readDate(int month, int year) {
        return YearMonth.of(month, year);
    }

    private void writeDate(DataOutputStream dos, YearMonth ym) throws IOException {
        dos.writeInt(ym.getYear());
        dos.writeInt(ym.getMonthValue());
    }
}

