import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { setFileData, byteSize, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './skill-proficiency.reducer';
import { ISkillProficiency } from 'app/shared/model/skill-proficiency.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISkillProficiencyUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SkillProficiencyUpdate = (props: ISkillProficiencyUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { skillProficiencyEntity, loading, updating } = props;

  const { description } = skillProficiencyEntity;

  const handleClose = () => {
    props.history.push('/skill-proficiency');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  const onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...skillProficiencyEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="esn4Dot0App.skillProficiency.home.createOrEditLabel" data-cy="SkillProficiencyCreateUpdateHeading">
            Create or edit a SkillProficiency
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : skillProficiencyEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="skill-proficiency-id">ID</Label>
                  <AvInput id="skill-proficiency-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="skill-proficiency-name">
                  Name
                </Label>
                <AvField id="skill-proficiency-name" data-cy="name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="skill-proficiency-description">
                  Description
                </Label>
                <AvInput id="skill-proficiency-description" data-cy="description" type="textarea" name="description" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/skill-proficiency" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  skillProficiencyEntity: storeState.skillProficiency.entity,
  loading: storeState.skillProficiency.loading,
  updating: storeState.skillProficiency.updating,
  updateSuccess: storeState.skillProficiency.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SkillProficiencyUpdate);
