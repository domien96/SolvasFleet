import { ACTION_LANG } from '../constants/constants.ts';

const changeLanguage = (lang: string) => {
  return { type: ACTION_LANG, lang };
}

export { changeLanguage };
