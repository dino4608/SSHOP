import { Product } from "../product/api";


export type ShippingAddress = {
    name: string;
    line1: string;
    line2?: string;
    city: string;
    state: string;
    postalCode: string;
    country: string;
};

export type OrderItem = {
    product: Product; // Reference to the product
    quantity: number;
    price: number;
};

export type Order = {
    orderNumber: string;
    orderDate: string; // ISO 8601 datetime string
    customerId: string;
    customerName: string;
    customerEmail: string;
    stripeCustomerId: string;
    stripeCheckoutSessionId: string;
    stripePaymentIntentId: string;
    totalPrice: string; // Represented as a string to handle currency precision
    shippingAddress: ShippingAddress;
    status: 'PROCESSING' | 'SHIPPED' | 'DELIVERED' | 'CANCELLED';
};
