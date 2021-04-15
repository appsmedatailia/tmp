import { IConsultantPreference } from 'app/shared/model/consultant-preference.model';
import { PreferenceRank } from 'app/shared/model/enumerations/preference-rank.model';

export interface IPreference {
  prefernce?: string | null;
  rank?: PreferenceRank | null;
  consultantPreference?: IConsultantPreference | null;
}

export const defaultValue: Readonly<IPreference> = {};
