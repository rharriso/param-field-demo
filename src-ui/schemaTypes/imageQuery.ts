/* tslint:disable */
/* eslint-disable */
// This file was automatically generated and should not be edited.

import { ImageSize } from "./globalTypes";

// ====================================================
// GraphQL query operation: imageQuery
// ====================================================

export interface imageQuery_fetchImage {
  __typename: "Image";
  title: string;
  url: string;
}

export interface imageQuery {
  fetchImage: imageQuery_fetchImage;
}

export interface imageQueryVariables {
  imageSize?: ImageSize | null;
}
