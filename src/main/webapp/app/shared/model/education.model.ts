import dayjs from 'dayjs';
import { IConsultant } from 'app/shared/model/consultant.model';

export interface IEducation {
  degree?: string | null;
  fieldOfStudy?: string | null;
  school?: string | null;
  location?: string | null;
  startDate?: string | null;
  endDate?: string | null;
  description?: string | null;
  website?: string | null;
  consultant?: IConsultant | null;
}

export const defaultValue: Readonly<IEducation> = {};
