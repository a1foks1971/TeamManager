document.addEventListener('DOMContentLoaded', () => {
  const langSwitcher = document.getElementById('lang-switcher');
  let lang = 'us'; // язык по умолчанию
//  let lang = 'uk'; // язык по умолчанию

  function setLanguage(lang) {
    document.querySelector('[data-page="home"] #page-title').textContent   = strings.home.teamman[lang];
    document.querySelector('[data-page="home"] #settings-btn').textContent = strings.home.settings[lang];
    document.querySelector('[data-page="home"] #next-btn').textContent     = strings.home.next[lang];
  }

  setLanguage(lang);

  langSwitcher.addEventListener('change', (e) => {
    lang = e.target.value;
    setLanguage(lang);
  });
});


//document.addEventListener('DOMContentLoaded', () => {
//  const langSwitcher = document.getElementById('lang-switcher');
//  let lang = 'uk'; // язык по умолчанию
//
////const page = document.body.dataset.page;
////
//  function setLanguage(lang) {
////    switch (page) {
////      case 'home':
//        document.getElementById('page-title').textContent   = strings.home.teamman[lang];
//        document.getElementById('settings-btn').textContent = strings.home.settings[lang];
//        document.getElementById('next-btn').textContent     = strings.home.next[lang];
//        break;
//
////      case 'settings':
//        document.getElementById('page-title').textContent = strings.settings.settings[lang];
//        document.getElementById('db-btn').textContent      = strings.settings.database[lang];
//        document.getElementById('users-btn').textContent   = strings.settings.users[lang];
//        break;
//
//      // сюда можно добавить другие страницы
////    }
//  }
//
//  }
//
//  setLanguage(lang);
//
//  langSwitcher.addEventListener('change', (e) => {
//    lang = e.target.value;
//    setLanguage(lang);
//  });
//});
