import { ApplicaPage } from './app.po';

describe('applica App', () => {
  let page: ApplicaPage;

  beforeEach(() => {
    page = new ApplicaPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!!');
  });
});
