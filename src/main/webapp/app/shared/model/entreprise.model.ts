import { IManager } from 'app/shared/model/manager.model';
import { IConsultant } from 'app/shared/model/consultant.model';
import { EntrepriseType } from 'app/shared/model/enumerations/entreprise-type.model';

export interface IEntreprise {
  id?: string;
  name?: string | null;
  logoContentType?: string | null;
  logo?: string | null;
  type?: EntrepriseType | null;
  presentation?: string | null;
  phone?: string | null;
  website?: string | null;
  managers?: IManager[] | null;
  consultants?: IConsultant[] | null;
}

export const defaultValue: Readonly<IEntreprise> = {};
