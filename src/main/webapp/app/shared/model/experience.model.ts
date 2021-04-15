import dayjs from 'dayjs';
import { IConsultant } from 'app/shared/model/consultant.model';

export interface IExperience {
  jobTitle?: string | null;
  entreprise?: string | null;
  location?: string | null;
  startDate?: string | null;
  endDate?: string | null;
  jobContext?: string | null;
  keyAchievement?: string | null;
  keyResponsibility?: string | null;
  consultant?: IConsultant | null;
}

export const defaultValue: Readonly<IExperience> = {};
