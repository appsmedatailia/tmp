import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SkillProficiency from './skill-proficiency';
import SkillProficiencyDetail from './skill-proficiency-detail';
import SkillProficiencyUpdate from './skill-proficiency-update';
import SkillProficiencyDeleteDialog from './skill-proficiency-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SkillProficiencyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SkillProficiencyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SkillProficiencyDetail} />
      <ErrorBoundaryRoute path={match.url} component={SkillProficiency} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SkillProficiencyDeleteDialog} />
  </>
);

export default Routes;
