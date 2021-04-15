import { IUser } from 'app/shared/model/user.model';
import { IEntreprise } from 'app/shared/model/entreprise.model';
import { IConsultant } from 'app/shared/model/consultant.model';
import { Civility } from 'app/shared/model/enumerations/civility.model';

export interface IManager {
  id?: string;
  civility?: Civility | null;
  fullName?: string | null;
  phone?: string | null;
  user?: IUser | null;
  entreprise?: IEntreprise | null;
  managedConsultants?: IConsultant[] | null;
}

export const defaultValue: Readonly<IManager> = {};
