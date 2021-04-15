import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { openFile, byteSize, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './entreprise.reducer';
import { IEntreprise } from 'app/shared/model/entreprise.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEntrepriseProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Entreprise = (props: IEntrepriseProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { entrepriseList, match, loading } = props;
  return (
    <div>
      <h2 id="entreprise-heading" data-cy="EntrepriseHeading">
        Entreprises
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Entreprise
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {entrepriseList && entrepriseList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Logo</th>
                <th>Type</th>
                <th>Presentation</th>
                <th>Phone</th>
                <th>Website</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {entrepriseList.map((entreprise, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${entreprise.id}`} color="link" size="sm">
                      {entreprise.id}
                    </Button>
                  </td>
                  <td>{entreprise.name}</td>
                  <td>
                    {entreprise.logo ? (
                      <div>
                        {entreprise.logoContentType ? (
                          <a onClick={openFile(entreprise.logoContentType, entreprise.logo)}>
                            <img src={`data:${entreprise.logoContentType};base64,${entreprise.logo}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {entreprise.logoContentType}, {byteSize(entreprise.logo)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{entreprise.type}</td>
                  <td>{entreprise.presentation}</td>
                  <td>{entreprise.phone}</td>
                  <td>{entreprise.website}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${entreprise.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${entreprise.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${entreprise.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Entreprises found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ entreprise }: IRootState) => ({
  entrepriseList: entreprise.entities,
  loading: entreprise.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Entreprise);
