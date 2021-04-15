import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IConsultant, defaultValue } from 'app/shared/model/consultant.model';

export const ACTION_TYPES = {
  FETCH_CONSULTANT_LIST: 'consultant/FETCH_CONSULTANT_LIST',
  FETCH_CONSULTANT: 'consultant/FETCH_CONSULTANT',
  CREATE_CONSULTANT: 'consultant/CREATE_CONSULTANT',
  UPDATE_CONSULTANT: 'consultant/UPDATE_CONSULTANT',
  PARTIAL_UPDATE_CONSULTANT: 'consultant/PARTIAL_UPDATE_CONSULTANT',
  DELETE_CONSULTANT: 'consultant/DELETE_CONSULTANT',
  RESET: 'consultant/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IConsultant>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ConsultantState = Readonly<typeof initialState>;

// Reducer

export default (state: ConsultantState = initialState, action): ConsultantState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CONSULTANT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CONSULTANT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CONSULTANT):
    case REQUEST(ACTION_TYPES.UPDATE_CONSULTANT):
    case REQUEST(ACTION_TYPES.DELETE_CONSULTANT):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_CONSULTANT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CONSULTANT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CONSULTANT):
    case FAILURE(ACTION_TYPES.CREATE_CONSULTANT):
    case FAILURE(ACTION_TYPES.UPDATE_CONSULTANT):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_CONSULTANT):
    case FAILURE(ACTION_TYPES.DELETE_CONSULTANT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONSULTANT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONSULTANT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CONSULTANT):
    case SUCCESS(ACTION_TYPES.UPDATE_CONSULTANT):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_CONSULTANT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CONSULTANT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/consultants';

// Actions

export const getEntities: ICrudGetAllAction<IConsultant> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CONSULTANT_LIST,
  payload: axios.get<IConsultant>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IConsultant> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CONSULTANT,
    payload: axios.get<IConsultant>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IConsultant> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CONSULTANT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IConsultant> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CONSULTANT,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IConsultant> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_CONSULTANT,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IConsultant> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CONSULTANT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
