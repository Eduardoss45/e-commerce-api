export interface CreateOrderRequest {
  items: {
    productId: number;
    quantity: number;
  }[];

  payment: {
    cardNumber: string;
    cvv: string;
  };
}
