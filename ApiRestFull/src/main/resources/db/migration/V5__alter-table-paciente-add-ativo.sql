alter table medicos add ativopaciente tinyint;
update medicos set ativopaciente = 1;
alter table pacientes modify ativo tinyint not null;