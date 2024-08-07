import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import translationRU from './translations/ru.json';

i18n
  .use(initReactI18next)
  .init({
    resources: {
      ru: {
        translation: translationRU
      }
    },
    lng: 'ru', // Язык по умолчанию
    fallbackLng: 'ru',
    interpolation: {
      escapeValue: false
    }
  });

export default i18n;