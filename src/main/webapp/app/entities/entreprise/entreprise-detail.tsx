import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './entreprise.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEntrepriseDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EntrepriseDetail = (props: IEntrepriseDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { entrepriseEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="entrepriseDetailsHeading">Entreprise</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{entrepriseEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{entrepriseEntity.name}</dd>
          <dt>
            <span id="logo">Logo</span>
          </dt>
          <dd>
            {entrepriseEntity.logo ? (
              <div>
                {entrepriseEntity.logoContentType ? (
                  <a onClick={openFile(entrepriseEntity.logoContentType, entrepriseEntity.logo)}>
                    <img src={`data:${entrepriseEntity.logoContentType};base64,${entrepriseEntity.logo}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {entrepriseEntity.logoContentType}, {byteSize(entrepriseEntity.logo)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{entrepriseEntity.type}</dd>
          <dt>
            <span id="presentation">Presentation</span>
          </dt>
          <dd>{entrepriseEntity.presentation}</dd>
          <dt>
            <span id="phone">Phone</span>
          </dt>
          <dd>{entrepriseEntity.phone}</dd>
          <dt>
            <span id="website">Website</span>
          </dt>
          <dd>{entrepriseEntity.website}</dd>
        </dl>
        <Button tag={Link} to="/entreprise" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/entreprise/${entrepriseEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ entreprise }: IRootState) => ({
  entrepriseEntity: entreprise.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EntrepriseDetail);
