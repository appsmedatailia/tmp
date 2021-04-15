import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IConsultantPreference, defaultValue } from 'app/shared/model/consultant-preference.model';

export const ACTION_TYPES = {
  FETCH_CONSULTANTPREFERENCE_LIST: 'consultantPreference/FETCH_CONSULTANTPREFERENCE_LIST',
  FETCH_CONSULTANTPREFERENCE: 'consultantPreference/FETCH_CONSULTANTPREFERENCE',
  CREATE_CONSULTANTPREFERENCE: 'consultantPreference/CREATE_CONSULTANTPREFERENCE',
  UPDATE_CONSULTANTPREFERENCE: 'consultantPreference/UPDATE_CONSULTANTPREFERENCE',
  PARTIAL_UPDATE_CONSULTANTPREFERENCE: 'consultantPreference/PARTIAL_UPDATE_CONSULTANTPREFERENCE',
  DELETE_CONSULTANTPREFERENCE: 'consultantPreference/DELETE_CONSULTANTPREFERENCE',
  SET_BLOB: 'consultantPreference/SET_BLOB',
  RESET: 'consultantPreference/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IConsultantPreference>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ConsultantPreferenceState = Readonly<typeof initialState>;

// Reducer

export default (state: ConsultantPreferenceState = initialState, action): ConsultantPreferenceState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CONSULTANTPREFERENCE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CONSULTANTPREFERENCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CONSULTANTPREFERENCE):
    case REQUEST(ACTION_TYPES.UPDATE_CONSULTANTPREFERENCE):
    case REQUEST(ACTION_TYPES.DELETE_CONSULTANTPREFERENCE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_CONSULTANTPREFERENCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CONSULTANTPREFERENCE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CONSULTANTPREFERENCE):
    case FAILURE(ACTION_TYPES.CREATE_CONSULTANTPREFERENCE):
    case FAILURE(ACTION_TYPES.UPDATE_CONSULTANTPREFERENCE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_CONSULTANTPREFERENCE):
    case FAILURE(ACTION_TYPES.DELETE_CONSULTANTPREFERENCE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONSULTANTPREFERENCE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONSULTANTPREFERENCE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CONSULTANTPREFERENCE):
    case SUCCESS(ACTION_TYPES.UPDATE_CONSULTANTPREFERENCE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_CONSULTANTPREFERENCE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CONSULTANTPREFERENCE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.SET_BLOB: {
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType,
        },
      };
    }
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/consultant-preferences';

// Actions

export const getEntities: ICrudGetAllAction<IConsultantPreference> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CONSULTANTPREFERENCE_LIST,
  payload: axios.get<IConsultantPreference>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IConsultantPreference> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CONSULTANTPREFERENCE,
    payload: axios.get<IConsultantPreference>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IConsultantPreference> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CONSULTANTPREFERENCE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IConsultantPreference> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CONSULTANTPREFERENCE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IConsultantPreference> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_CONSULTANTPREFERENCE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IConsultantPreference> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CONSULTANTPREFERENCE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType,
  },
});

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
