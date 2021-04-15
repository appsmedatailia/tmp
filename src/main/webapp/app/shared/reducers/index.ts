import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import entreprise, {
  EntrepriseState
} from 'app/entities/entreprise/entreprise.reducer';
// prettier-ignore
import manager, {
  ManagerState
} from 'app/entities/manager/manager.reducer';
// prettier-ignore
import consultant, {
  ConsultantState
} from 'app/entities/consultant/consultant.reducer';
// prettier-ignore
import skillProficiency, {
  SkillProficiencyState
} from 'app/entities/skill-proficiency/skill-proficiency.reducer';
// prettier-ignore
import consultantPreference, {
  ConsultantPreferenceState
} from 'app/entities/consultant-preference/consultant-preference.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly entreprise: EntrepriseState;
  readonly manager: ManagerState;
  readonly consultant: ConsultantState;
  readonly skillProficiency: SkillProficiencyState;
  readonly consultantPreference: ConsultantPreferenceState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  entreprise,
  manager,
  consultant,
  skillProficiency,
  consultantPreference,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
