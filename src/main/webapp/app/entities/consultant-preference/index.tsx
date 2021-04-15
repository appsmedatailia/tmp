import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ConsultantPreference from './consultant-preference';
import ConsultantPreferenceDetail from './consultant-preference-detail';
import ConsultantPreferenceUpdate from './consultant-preference-update';
import ConsultantPreferenceDeleteDialog from './consultant-preference-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ConsultantPreferenceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ConsultantPreferenceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ConsultantPreferenceDetail} />
      <ErrorBoundaryRoute path={match.url} component={ConsultantPreference} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ConsultantPreferenceDeleteDialog} />
  </>
);

export default Routes;
