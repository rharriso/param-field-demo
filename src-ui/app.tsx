import * as React from 'react';
import { render } from 'react-dom'

import { ApolloProvider, Query } from 'react-apollo';

import apolloClient from 'src-ui/apolloClient';
import gql from 'graphql-tag';
import { imageQuery } from './schemaTypes/imageQuery';

const imageQueryGQL = gql`
query imageQuery {
 	fetchImage {
 	  title
 	  url
 	}
}
`;

class AppRoot extends React.Component {
  public render() {
    return (
      <Query<imageQuery> query={imageQueryGQL}>
        {({ loading, data }) => {
          if (loading) {
            return <h1>Loading</h1>;
          }

          if (!data) {
            return <h1>No Data Returned</h1>;
          }

          const { url, title } = data.fetchImage;

          return (
            <React.Fragment>
              <img src={url} alt={title} />
              <h2>{title}</h2>
            </React.Fragment>
          )
        }}
      </Query>
    );
  }
}
const App = () => (
  <ApolloProvider client={apolloClient}>
    <AppRoot />
  </ApolloProvider>
);

render(<App />, document.getElementById('root'));
