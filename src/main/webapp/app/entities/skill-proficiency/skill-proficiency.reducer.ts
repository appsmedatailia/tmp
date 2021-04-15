import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISkillProficiency, defaultValue } from 'app/shared/model/skill-proficiency.model';

export const ACTION_TYPES = {
  FETCH_SKILLPROFICIENCY_LIST: 'skillProficiency/FETCH_SKILLPROFICIENCY_LIST',
  FETCH_SKILLPROFICIENCY: 'skillProficiency/FETCH_SKILLPROFICIENCY',
  CREATE_SKILLPROFICIENCY: 'skillProficiency/CREATE_SKILLPROFICIENCY',
  UPDATE_SKILLPROFICIENCY: 'skillProficiency/UPDATE_SKILLPROFICIENCY',
  PARTIAL_UPDATE_SKILLPROFICIENCY: 'skillProficiency/PARTIAL_UPDATE_SKILLPROFICIENCY',
  DELETE_SKILLPROFICIENCY: 'skillProficiency/DELETE_SKILLPROFICIENCY',
  SET_BLOB: 'skillProficiency/SET_BLOB',
  RESET: 'skillProficiency/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISkillProficiency>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type SkillProficiencyState = Readonly<typeof initialState>;

// Reducer

export default (state: SkillProficiencyState = initialState, action): SkillProficiencyState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SKILLPROFICIENCY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SKILLPROFICIENCY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SKILLPROFICIENCY):
    case REQUEST(ACTION_TYPES.UPDATE_SKILLPROFICIENCY):
    case REQUEST(ACTION_TYPES.DELETE_SKILLPROFICIENCY):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_SKILLPROFICIENCY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SKILLPROFICIENCY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SKILLPROFICIENCY):
    case FAILURE(ACTION_TYPES.CREATE_SKILLPROFICIENCY):
    case FAILURE(ACTION_TYPES.UPDATE_SKILLPROFICIENCY):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_SKILLPROFICIENCY):
    case FAILURE(ACTION_TYPES.DELETE_SKILLPROFICIENCY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SKILLPROFICIENCY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SKILLPROFICIENCY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SKILLPROFICIENCY):
    case SUCCESS(ACTION_TYPES.UPDATE_SKILLPROFICIENCY):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_SKILLPROFICIENCY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SKILLPROFICIENCY):
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

const apiUrl = 'api/skill-proficiencies';

// Actions

export const getEntities: ICrudGetAllAction<ISkillProficiency> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SKILLPROFICIENCY_LIST,
  payload: axios.get<ISkillProficiency>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ISkillProficiency> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SKILLPROFICIENCY,
    payload: axios.get<ISkillProficiency>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISkillProficiency> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SKILLPROFICIENCY,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISkillProficiency> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SKILLPROFICIENCY,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ISkillProficiency> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_SKILLPROFICIENCY,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISkillProficiency> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SKILLPROFICIENCY,
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
