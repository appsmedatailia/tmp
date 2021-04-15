import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Entreprise from './entreprise';
import Manager from './manager';
import Consultant from './consultant';
import SkillProficiency from './skill-proficiency';
import ConsultantPreference from './consultant-preference';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}entreprise`} component={Entreprise} />
      <ErrorBoundaryRoute path={`${match.url}manager`} component={Manager} />
      <ErrorBoundaryRoute path={`${match.url}consultant`} component={Consultant} />
      <ErrorBoundaryRoute path={`${match.url}skill-proficiency`} component={SkillProficiency} />
      <ErrorBoundaryRoute path={`${match.url}consultant-preference`} component={ConsultantPreference} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
