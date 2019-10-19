import * as React from 'react';
import { render } from 'react-dom'

import { ApolloProvider, Query } from 'react-apollo';

import apolloClient from 'src-ui/apolloClient';
import gql from 'graphql-tag';
import { imageQuery, imageQueryVariables } from './schemaTypes/imageQuery';
import { ImageSize } from './schemaTypes/globalTypes';

const imageQueryGQL = gql`
query imageQuery  ($imageSize: ImageSize){
 	fetchImage (imageSize: $imageSize){
 	  title
 	  url
 	}
}
`;

class AppRoot extends React.Component<{}, { imageSize: ImageSize}> {
  public state = { imageSize: ImageSize.small }

  public render() {
    return (
      <Query<imageQuery, imageQueryVariables> query={imageQueryGQL} variables={{ imageSize: this.state.imageSize }}>
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
              <img src={url} alt={title} onClick={this.toggleImageSize} />
              <h2>{title}</h2>
            </React.Fragment>
          )
        }}
      </Query>
    );
  }

  private toggleImageSize = () => {
    const newImageSize = this.state.imageSize == ImageSize.small ? ImageSize.original : ImageSize.small;
    this.setState({ imageSize: newImageSize });
  }
}
const App = () => (
  <ApolloProvider client={apolloClient}>
    <AppRoot />
  </ApolloProvider>
);

render(<App />, document.getElementById('root'));
