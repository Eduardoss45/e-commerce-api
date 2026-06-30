export type OrdersStatus = 'CREATED' | 'PROCESSING' | 'PAID' | 'FAILED' | 'COMPLETED';

export interface Order {
    id: string;
    product: string;
    quantity: number;
    status: OrdersStatus;
    createdAt: number;
}