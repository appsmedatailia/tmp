import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './consultant.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IConsultantDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ConsultantDetail = (props: IConsultantDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { consultantEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="consultantDetailsHeading">Consultant</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{consultantEntity.id}</dd>
          <dt>
            <span id="civility">Civility</span>
          </dt>
          <dd>{consultantEntity.civility}</dd>
          <dt>
            <span id="fullName">Full Name</span>
          </dt>
          <dd>{consultantEntity.fullName}</dd>
          <dt>
            <span id="phone">Phone</span>
          </dt>
          <dd>{consultantEntity.phone}</dd>
          <dt>
            <span id="state">State</span>
          </dt>
          <dd>{consultantEntity.state}</dd>
          <dt>User</dt>
          <dd>{consultantEntity.user ? consultantEntity.user.login : ''}</dd>
          <dt>Entreprise</dt>
          <dd>{consultantEntity.entreprise ? consultantEntity.entreprise.name : ''}</dd>
          <dt>Manager</dt>
          <dd>{consultantEntity.manager ? consultantEntity.manager.fullName : ''}</dd>
        </dl>
        <Button tag={Link} to="/consultant" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/consultant/${consultantEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ consultant }: IRootState) => ({
  consultantEntity: consultant.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ConsultantDetail);
