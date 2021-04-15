import { IUser } from 'app/shared/model/user.model';
import { IEntreprise } from 'app/shared/model/entreprise.model';
import { IManager } from 'app/shared/model/manager.model';
import { IExperience } from 'app/shared/model/experience.model';
import { IEducation } from 'app/shared/model/education.model';
import { ICertification } from 'app/shared/model/certification.model';
import { ISkill } from 'app/shared/model/skill.model';
import { IConsultantPreference } from 'app/shared/model/consultant-preference.model';
import { Civility } from 'app/shared/model/enumerations/civility.model';
import { ConsultantState } from 'app/shared/model/enumerations/consultant-state.model';

export interface IConsultant {
  id?: string;
  civility?: Civility | null;
  fullName?: string | null;
  phone?: string | null;
  state?: ConsultantState | null;
  user?: IUser | null;
  entreprise?: IEntreprise | null;
  manager?: IManager | null;
  experiences?: IExperience[] | null;
  educations?: IEducation[] | null;
  certifications?: ICertification[] | null;
  skills?: ISkill[] | null;
  preferences?: IConsultantPreference[] | null;
}

export const defaultValue: Readonly<IConsultant> = {};
