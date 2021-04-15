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
import { getEntity, updateEntity, createEntity, reset } from './manager.reducer';
import { IManager } from 'app/shared/model/manager.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IManagerUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ManagerUpdate = (props: IManagerUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { managerEntity, users, entreprises, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/manager');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getUsers();
    props.getEntreprises();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...managerEntity,
        ...values,
        user: users.find(it => it.id.toString() === values.userId.toString()),
        entreprise: entreprises.find(it => it.id.toString() === values.entrepriseId.toString()),
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
          <h2 id="esn4Dot0App.manager.home.createOrEditLabel" data-cy="ManagerCreateUpdateHeading">
            Create or edit a Manager
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : managerEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="manager-id">ID</Label>
                  <AvInput id="manager-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="civilityLabel" for="manager-civility">
                  Civility
                </Label>
                <AvInput
                  id="manager-civility"
                  data-cy="civility"
                  type="select"
                  className="form-control"
                  name="civility"
                  value={(!isNew && managerEntity.civility) || 'MR'}
                >
                  <option value="MR">Mr</option>
                  <option value="MRS">Mrs</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="fullNameLabel" for="manager-fullName">
                  Full Name
                </Label>
                <AvField id="manager-fullName" data-cy="fullName" type="text" name="fullName" />
              </AvGroup>
              <AvGroup>
                <Label id="phoneLabel" for="manager-phone">
                  Phone
                </Label>
                <AvField id="manager-phone" data-cy="phone" type="text" name="phone" />
              </AvGroup>
              <AvGroup>
                <Label for="manager-user">User</Label>
                <AvInput id="manager-user" data-cy="user" type="select" className="form-control" name="userId">
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
                <Label for="manager-entreprise">Entreprise</Label>
                <AvInput id="manager-entreprise" data-cy="entreprise" type="select" className="form-control" name="entrepriseId">
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
              <Button tag={Link} id="cancel-save" to="/manager" replace color="info">
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
  managerEntity: storeState.manager.entity,
  loading: storeState.manager.loading,
  updating: storeState.manager.updating,
  updateSuccess: storeState.manager.updateSuccess,
});

const mapDispatchToProps = {
  getUsers,
  getEntreprises,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ManagerUpdate);
