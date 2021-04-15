import dayjs from 'dayjs';
import { IConsultant } from 'app/shared/model/consultant.model';

export interface ICertification {
  name?: string | null;
  description?: string | null;
  issueDate?: string | null;
  endDate?: string | null;
  issuingOrganization?: string | null;
  credentialID?: string | null;
  credentialURL?: string | null;
  consultant?: IConsultant | null;
}

export const defaultValue: Readonly<ICertification> = {};
