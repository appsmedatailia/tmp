import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './consultant.reducer';
import { IConsultant } from 'app/shared/model/consultant.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IConsultantProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Consultant = (props: IConsultantProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { consultantList, match, loading } = props;
  return (
    <div>
      <h2 id="consultant-heading" data-cy="ConsultantHeading">
        Consultants
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Consultant
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {consultantList && consultantList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Civility</th>
                <th>Full Name</th>
                <th>Phone</th>
                <th>State</th>
                <th>User</th>
                <th>Entreprise</th>
                <th>Manager</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {consultantList.map((consultant, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${consultant.id}`} color="link" size="sm">
                      {consultant.id}
                    </Button>
                  </td>
                  <td>{consultant.civility}</td>
                  <td>{consultant.fullName}</td>
                  <td>{consultant.phone}</td>
                  <td>{consultant.state}</td>
                  <td>{consultant.user ? consultant.user.login : ''}</td>
                  <td>
                    {consultant.entreprise ? <Link to={`entreprise/${consultant.entreprise.id}`}>{consultant.entreprise.name}</Link> : ''}
                  </td>
                  <td>{consultant.manager ? <Link to={`manager/${consultant.manager.id}`}>{consultant.manager.fullName}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${consultant.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${consultant.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${consultant.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Consultants found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ consultant }: IRootState) => ({
  consultantList: consultant.entities,
  loading: consultant.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Consultant);
