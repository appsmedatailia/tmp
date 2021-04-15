import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { byteSize, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './consultant-preference.reducer';
import { IConsultantPreference } from 'app/shared/model/consultant-preference.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IConsultantPreferenceProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const ConsultantPreference = (props: IConsultantPreferenceProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { consultantPreferenceList, match, loading } = props;
  return (
    <div>
      <h2 id="consultant-preference-heading" data-cy="ConsultantPreferenceHeading">
        Consultant Preferences
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Consultant Preference
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {consultantPreferenceList && consultantPreferenceList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Motivation</th>
                <th>Criterion</th>
                <th>Priority</th>
                <th>Consultant</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {consultantPreferenceList.map((consultantPreference, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${consultantPreference.id}`} color="link" size="sm">
                      {consultantPreference.id}
                    </Button>
                  </td>
                  <td>{consultantPreference.motivation}</td>
                  <td>{consultantPreference.criterion}</td>
                  <td>{consultantPreference.priority}</td>
                  <td>
                    {consultantPreference.consultant ? (
                      <Link to={`consultant/${consultantPreference.consultant.id}`}>{consultantPreference.consultant.fullName}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`${match.url}/${consultantPreference.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${consultantPreference.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${consultantPreference.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Consultant Preferences found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ consultantPreference }: IRootState) => ({
  consultantPreferenceList: consultantPreference.entities,
  loading: consultantPreference.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ConsultantPreference);
