import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './consultant-preference.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IConsultantPreferenceDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ConsultantPreferenceDetail = (props: IConsultantPreferenceDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { consultantPreferenceEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="consultantPreferenceDetailsHeading">ConsultantPreference</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{consultantPreferenceEntity.id}</dd>
          <dt>
            <span id="motivation">Motivation</span>
          </dt>
          <dd>{consultantPreferenceEntity.motivation}</dd>
          <dt>
            <span id="criterion">Criterion</span>
          </dt>
          <dd>{consultantPreferenceEntity.criterion}</dd>
          <dt>
            <span id="priority">Priority</span>
          </dt>
          <dd>{consultantPreferenceEntity.priority}</dd>
          <dt>Consultant</dt>
          <dd>{consultantPreferenceEntity.consultant ? consultantPreferenceEntity.consultant.fullName : ''}</dd>
        </dl>
        <Button tag={Link} to="/consultant-preference" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/consultant-preference/${consultantPreferenceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ consultantPreference }: IRootState) => ({
  consultantPreferenceEntity: consultantPreference.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ConsultantPreferenceDetail);
