import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './manager.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IManagerDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ManagerDetail = (props: IManagerDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { managerEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="managerDetailsHeading">Manager</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{managerEntity.id}</dd>
          <dt>
            <span id="civility">Civility</span>
          </dt>
          <dd>{managerEntity.civility}</dd>
          <dt>
            <span id="fullName">Full Name</span>
          </dt>
          <dd>{managerEntity.fullName}</dd>
          <dt>
            <span id="phone">Phone</span>
          </dt>
          <dd>{managerEntity.phone}</dd>
          <dt>User</dt>
          <dd>{managerEntity.user ? managerEntity.user.login : ''}</dd>
          <dt>Entreprise</dt>
          <dd>{managerEntity.entreprise ? managerEntity.entreprise.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/manager" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/manager/${managerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ manager }: IRootState) => ({
  managerEntity: manager.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ManagerDetail);
