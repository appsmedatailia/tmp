import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './skill-proficiency.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISkillProficiencyDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SkillProficiencyDetail = (props: ISkillProficiencyDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { skillProficiencyEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="skillProficiencyDetailsHeading">SkillProficiency</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{skillProficiencyEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{skillProficiencyEntity.name}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{skillProficiencyEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/skill-proficiency" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/skill-proficiency/${skillProficiencyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ skillProficiency }: IRootState) => ({
  skillProficiencyEntity: skillProficiency.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SkillProficiencyDetail);
