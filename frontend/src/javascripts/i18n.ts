import T from 'i18n-react';
import { Store } from 'react-redux';

export const languages = {
  en: require('../../translations/en.yml'),
  nl: require('../../translations/nl.yml'),
};

T.setTexts(languages.en);

export default (store: Store<any>) => {
  return () => {
    T.setTexts(require(`../../translations/${store.getState().lang}.yml`));
  };
};
