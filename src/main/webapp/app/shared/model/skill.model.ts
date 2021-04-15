import { IConsultant } from 'app/shared/model/consultant.model';
import { SkillType } from 'app/shared/model/enumerations/skill-type.model';

export interface ISkill {
  name?: string | null;
  type?: SkillType | null;
  proficiency?: string | null;
  consultant?: IConsultant | null;
}

export const defaultValue: Readonly<ISkill> = {};
