package com.twodonik.webapp.storage.serializer;

import com.twodonik.webapp.exception.StorageException;
import com.twodonik.webapp.model.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.time.YearMonth;
import java.util.List;
import java.util.function.BiConsumer;

@FunctionalInterface
public interface DataStreamConsumer<T, U> extends BiConsumer<T, U> {
    @Override
    void accept(T t, U u);

    static void write(DataOutputStream dos, AbstractSection as, SectionType st) {
        try {
            dos.writeUTF(st.name());
            switch (st) {
                case OBJECTIVE:
                case PERSONAL:
                    dos.writeUTF(((TextSection) as).getContent());
                    break;
                case ACHIEVEMENT:
                case QUALIFICATION:
                    writeListSection(dos, as);
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    writeOrganizationSection(dos, as);
                    break;
            }
        } catch (IOException e) {
            throw new StorageException("IOExceprion", e);
        }
    }

    static void writeListSection(DataOutputStream dos, AbstractSection section) throws IOException {
        List<String> ach = ((ListSection) section).getItems();
        int size = ach.size();
        dos.writeInt(size);

        for (int i = 0; i < size; i++) {
            dos.writeUTF(ach.get(i));
        }
    }

    static void writeOrganizationSection(DataOutputStream dos, AbstractSection section) throws IOException {
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
    static void writeDate(DataOutputStream dos, YearMonth ym) throws IOException {
        dos.writeInt(ym.getYear());
        dos.writeInt(ym.getMonthValue());
    }
}
