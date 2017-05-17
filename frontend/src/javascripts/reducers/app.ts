import { ACTION_LANG } from '../constants/constants.ts';

export interface State {
  lang: string;
}

interface Action {
  type: string;
  lang: string;
}

const initialState: () => State = () => {
  return { lang: 'en' };
};

export default (state = initialState(), action: Action) => {
  if (action.type === ACTION_LANG) {
    return { lang: action.lang };
  }

  return state;
};
