import * as React from 'react';
import { render } from 'react-dom'

import { ApolloProvider } from 'react-apollo';

import apolloClient from 'src-ui/apolloClient';

class AppRoot extends React.Component {
  public render() {
    return (
      <h1>Hello</h1>
    );
  }
}
const App = () => (
  <ApolloProvider client={apolloClient}>
    <AppRoot />
  </ApolloProvider>
);

render(<App />, document.getElementById('root'));
