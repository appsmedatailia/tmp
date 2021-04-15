import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IEntreprise } from 'app/shared/model/entreprise.model';
import { getEntities as getEntreprises } from 'app/entities/entreprise/entreprise.reducer';
import { IManager } from 'app/shared/model/manager.model';
import { getEntities as getManagers } from 'app/entities/manager/manager.reducer';
import { getEntity, updateEntity, createEntity, reset } from './consultant.reducer';
import { IConsultant } from 'app/shared/model/consultant.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IConsultantUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ConsultantUpdate = (props: IConsultantUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { consultantEntity, users, entreprises, managers, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/consultant');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getUsers();
    props.getEntreprises();
    props.getManagers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...consultantEntity,
        ...values,
        user: users.find(it => it.id.toString() === values.userId.toString()),
        entreprise: entreprises.find(it => it.id.toString() === values.entrepriseId.toString()),
        manager: managers.find(it => it.id.toString() === values.managerId.toString()),
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
          <h2 id="esn4Dot0App.consultant.home.createOrEditLabel" data-cy="ConsultantCreateUpdateHeading">
            Create or edit a Consultant
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : consultantEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="consultant-id">ID</Label>
                  <AvInput id="consultant-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="civilityLabel" for="consultant-civility">
                  Civility
                </Label>
                <AvInput
                  id="consultant-civility"
                  data-cy="civility"
                  type="select"
                  className="form-control"
                  name="civility"
                  value={(!isNew && consultantEntity.civility) || 'MR'}
                >
                  <option value="MR">Mr</option>
                  <option value="MRS">Mrs</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="fullNameLabel" for="consultant-fullName">
                  Full Name
                </Label>
                <AvField id="consultant-fullName" data-cy="fullName" type="text" name="fullName" />
              </AvGroup>
              <AvGroup>
                <Label id="phoneLabel" for="consultant-phone">
                  Phone
                </Label>
                <AvField id="consultant-phone" data-cy="phone" type="text" name="phone" />
              </AvGroup>
              <AvGroup>
                <Label id="stateLabel" for="consultant-state">
                  State
                </Label>
                <AvInput
                  id="consultant-state"
                  data-cy="state"
                  type="select"
                  className="form-control"
                  name="state"
                  value={(!isNew && consultantEntity.state) || 'IN_BETWEEN_CONTRACTS'}
                >
                  <option value="IN_BETWEEN_CONTRACTS">InBetweenContracts</option>
                  <option value="ON_ASSIGNMENT">OnAssignment</option>
                  <option value="NOT_APPLICABLE">NotApplicable</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="consultant-user">User</Label>
                <AvInput id="consultant-user" data-cy="user" type="select" className="form-control" name="userId">
                  <option value="" key="0" />
                  {users
                    ? users.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.login}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="consultant-entreprise">Entreprise</Label>
                <AvInput id="consultant-entreprise" data-cy="entreprise" type="select" className="form-control" name="entrepriseId">
                  <option value="" key="0" />
                  {entreprises
                    ? entreprises.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="consultant-manager">Manager</Label>
                <AvInput id="consultant-manager" data-cy="manager" type="select" className="form-control" name="managerId">
                  <option value="" key="0" />
                  {managers
                    ? managers.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.fullName}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/consultant" replace color="info">
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
  users: storeState.userManagement.users,
  entreprises: storeState.entreprise.entities,
  managers: storeState.manager.entities,
  consultantEntity: storeState.consultant.entity,
  loading: storeState.consultant.loading,
  updating: storeState.consultant.updating,
  updateSuccess: storeState.consultant.updateSuccess,
});

const mapDispatchToProps = {
  getUsers,
  getEntreprises,
  getManagers,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ConsultantUpdate);
