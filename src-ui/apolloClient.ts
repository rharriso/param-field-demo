import { InMemoryCache } from 'apollo-boost';
import { ApolloClient } from 'apollo-client';
import { createHttpLink } from 'apollo-link-http';

const httpLink = createHttpLink({
  uri: 'http://localhost:5000/graphql'
});

const apolloClient = new ApolloClient({
  link: httpLink,
  cache: new InMemoryCache()
});

export default apolloClient;
