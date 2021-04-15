import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Consultant from './consultant';
import ConsultantDetail from './consultant-detail';
import ConsultantUpdate from './consultant-update';
import ConsultantDeleteDialog from './consultant-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ConsultantUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ConsultantUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ConsultantDetail} />
      <ErrorBoundaryRoute path={match.url} component={Consultant} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ConsultantDeleteDialog} />
  </>
);

export default Routes;
