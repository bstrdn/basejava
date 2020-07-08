insert into resume (uuid, full_name)
VALUES ('UUID_1', 'sdfdsf'),
       ('UUID_2', 'sdfdsf'),
       ('UUID_3', 'sddsfdfdsf'),
       ('UUID_4', 'sddsfsdfdfdsf');


insert into contact (resume_uuid, type, value) VALUES ('UUID_1', 'email', 'sdf@r.ru'), ('UUID_1', 'tel', '55555'),
                                                      ('UUID_4', 'email', '4sdf@r.ru'), ('UUID_4', 'tel', '455555');

select r.uuid, c.type, c.value
from resume r
         inner join contact c on r.uuid = c.resume_uuid
where uuid = 'UUID_1';

select r.uuid, r.full_name, c.value, c.resume_uuid
from resume r
         inner join contact c on r.uuid = c.resume_uuid
where uuid = 'd9a071d8-2539-4803-97cb-adc264edda28';