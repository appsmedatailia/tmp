import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { setFileData, byteSize, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IConsultant } from 'app/shared/model/consultant.model';
import { getEntities as getConsultants } from 'app/entities/consultant/consultant.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './consultant-preference.reducer';
import { IConsultantPreference } from 'app/shared/model/consultant-preference.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IConsultantPreferenceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ConsultantPreferenceUpdate = (props: IConsultantPreferenceUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { consultantPreferenceEntity, consultants, loading, updating } = props;

  const { motivation } = consultantPreferenceEntity;

  const handleClose = () => {
    props.history.push('/consultant-preference');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getConsultants();
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
        ...consultantPreferenceEntity,
        ...values,
        consultant: consultants.find(it => it.id.toString() === values.consultantId.toString()),
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
          <h2 id="esn4Dot0App.consultantPreference.home.createOrEditLabel" data-cy="ConsultantPreferenceCreateUpdateHeading">
            Create or edit a ConsultantPreference
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : consultantPreferenceEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="consultant-preference-id">ID</Label>
                  <AvInput id="consultant-preference-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="motivationLabel" for="consultant-preference-motivation">
                  Motivation
                </Label>
                <AvInput id="consultant-preference-motivation" data-cy="motivation" type="textarea" name="motivation" />
              </AvGroup>
              <AvGroup>
                <Label id="criterionLabel" for="consultant-preference-criterion">
                  Criterion
                </Label>
                <AvInput
                  id="consultant-preference-criterion"
                  data-cy="criterion"
                  type="select"
                  className="form-control"
                  name="criterion"
                  value={(!isNew && consultantPreferenceEntity.criterion) || 'BY_INDUSTRY_SECTOR'}
                >
                  <option value="BY_INDUSTRY_SECTOR">ByIndustrySector</option>
                  <option value="BY_DOMAIN">ByDomain</option>
                  <option value="BY_TECHNO">By_Techno</option>
                  <option value="BY_ENTREPRISE">ByEntreprise</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="priorityLabel" for="consultant-preference-priority">
                  Priority
                </Label>
                <AvInput
                  id="consultant-preference-priority"
                  data-cy="priority"
                  type="select"
                  className="form-control"
                  name="priority"
                  value={(!isNew && consultantPreferenceEntity.priority) || 'HIGH'}
                >
                  <option value="HIGH">High</option>
                  <option value="MEDIUM">Medium</option>
                  <option value="LOW">Low</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="consultant-preference-consultant">Consultant</Label>
                <AvInput
                  id="consultant-preference-consultant"
                  data-cy="consultant"
                  type="select"
                  className="form-control"
                  name="consultantId"
                >
                  <option value="" key="0" />
                  {consultants
                    ? consultants.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.fullName}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/consultant-preference" replace color="info">
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
  consultants: storeState.consultant.entities,
  consultantPreferenceEntity: storeState.consultantPreference.entity,
  loading: storeState.consultantPreference.loading,
  updating: storeState.consultantPreference.updating,
  updateSuccess: storeState.consultantPreference.updateSuccess,
});

const mapDispatchToProps = {
  getConsultants,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ConsultantPreferenceUpdate);
