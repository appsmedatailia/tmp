import { IConsultant } from 'app/shared/model/consultant.model';
import { IPreference } from 'app/shared/model/preference.model';
import { PreferenceCriterion } from 'app/shared/model/enumerations/preference-criterion.model';
import { Priority } from 'app/shared/model/enumerations/priority.model';

export interface IConsultantPreference {
  id?: string;
  motivation?: string | null;
  criterion?: PreferenceCriterion | null;
  priority?: Priority | null;
  consultant?: IConsultant | null;
  preferenceLists?: IPreference[] | null;
}

export const defaultValue: Readonly<IConsultantPreference> = {};
