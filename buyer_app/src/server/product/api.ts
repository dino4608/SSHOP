export type Product = {
    id: string;
    name: string;
    image: string;
    price: number;
    description: string;
    category: string;
};

export const getAllProducts = async (): Promise<Product[]> => {
    try {
        // Demo data
        const products: Product[] = [
            { id: '1', name: 'Product 1', image: 'image1.jpg', price: 100, description: 'Description for Product 1', category: 'Category 1' },
            { id: '2', name: 'Product 2', image: 'image2.jpg', price: 200, description: 'Description for Product 2', category: 'Category 2' },
            { id: '3', name: 'Product 3', image: 'image3.jpg', price: 300, description: 'Description for Product 3', category: 'Category 3' },
            { id: '4', name: 'Product 4', image: 'image4.jpg', price: 400, description: 'Description for Product 4', category: 'Category 4' },
            { id: '5', name: 'Product 5', image: 'image5.jpg', price: 500, description: 'Description for Product 5', category: 'Category 5' },
            { id: '6', name: 'Product 6', image: 'image6.jpg', price: 600, description: 'Description for Product 6', category: 'Category 6' },
            { id: '7', name: 'Product 7', image: 'image7.jpg', price: 700, description: 'Description for Product 7', category: 'Category 7' },
            { id: '8', name: 'Product 8', image: 'image8.jpg', price: 800, description: 'Description for Product 8', category: 'Category 8' },
            { id: '9', name: 'Product 9', image: 'image9.jpg', price: 900, description: 'Description for Product 9', category: 'Category 9' },
            { id: '10', name: 'Product 10', image: 'image10.jpg', price: 1000, description: 'Description for Product 10', category: 'Category 10' },
            { id: '11', name: 'Product 11', image: 'image11.jpg', price: 1100, description: 'Description for Product 11', category: 'Category 11' },
            { id: '12', name: 'Product 12', image: 'image12.jpg', price: 1200, description: 'Description for Product 12', category: 'Category 12' },
            { id: '13', name: 'Product 13', image: 'image13.jpg', price: 1300, description: 'Description for Product 13', category: 'Category 13' },
            { id: '14', name: 'Product 14', image: 'image14.jpg', price: 1400, description: 'Description for Product 14', category: 'Category 14' },
            { id: '15', name: 'Product 15', image: 'image15.jpg', price: 1500, description: 'Description for Product 15', category: 'Category 15' },
            { id: '16', name: 'Product 16', image: 'image16.jpg', price: 1600, description: 'Description for Product 16', category: 'Category 16' },
            { id: '17', name: 'Product 17', image: 'image17.jpg', price: 1700, description: 'Description for Product 17', category: 'Category 17' },
            { id: '18', name: 'Product 18', image: 'image18.jpg', price: 1800, description: 'Description for Product 18', category: 'Category 18' },
            { id: '19', name: 'Product 19', image: 'image19.jpg', price: 1900, description: 'Description for Product 19', category: 'Category 19' },
            { id: '20', name: 'Product 20', image: 'image20.jpg', price: 2000, description: 'Description for Product 20', category: 'Category 20' },
            { id: '21', name: 'Product 21', image: 'image21.jpg', price: 2100, description: 'Description for Product 21', category: 'Category 21' },
            { id: '22', name: 'Product 22', image: 'image22.jpg', price: 2200, description: 'Description for Product 22', category: 'Category 22' },
            { id: '23', name: 'Product 23', image: 'image23.jpg', price: 2300, description: 'Description for Product 23', category: 'Category 23' }
        ];
        return products;
    } catch (error) {
        console.error('Error fetching products: ', error);
        return [];
    }
};

export const getProductsByCategorySlug = async (categorySlug: string): Promise<Product[]> => {
    try {
        // Demo data
        const products: Product[] = [
            { id: '1', name: 'Fashion Product 1', image: 'fashion1.jpg', price: 100, description: 'Description for Fashion Product 1', category: 'fashion' },
            { id: '2', name: 'Fashion Product 2', image: 'fashion2.jpg', price: 200, description: 'Description for Fashion Product 2', category: 'fashion' },
            { id: '3', name: 'Fashion Product 3', image: 'fashion3.jpg', price: 300, description: 'Description for Fashion Product 3', category: 'fashion' },
            { id: '4', name: 'Fashion Product 4', image: 'fashion4.jpg', price: 400, description: 'Description for Fashion Product 4', category: 'fashion' },
            { id: '5', name: 'Fashion Product 5', image: 'fashion5.jpg', price: 500, description: 'Description for Fashion Product 5', category: 'fashion' },
            { id: '6', name: 'Fashion Product 6', image: 'fashion6.jpg', price: 600, description: 'Description for Fashion Product 6', category: 'fashion' },
            { id: '7', name: 'Fashion Product 7', image: 'fashion7.jpg', price: 700, description: 'Description for Fashion Product 7', category: 'fashion' },
            { id: '8', name: 'Fashion Product 8', image: 'fashion8.jpg', price: 800, description: 'Description for Fashion Product 8', category: 'fashion' },
            { id: '9', name: 'Fashion Product 9', image: 'fashion9.jpg', price: 900, description: 'Description for Fashion Product 9', category: 'fashion' },
            { id: '10', name: 'Fashion Product 10', image: 'fashion10.jpg', price: 1000, description: 'Description for Fashion Product 10', category: 'fashion' }
        ];
        return products;
    } catch (error) {
        console.error('Error fetching products by category slug: ', error);
        return [];
    }
};

export const searchProducts = async (query: string): Promise<Product[]> => {
    try {
        // Demo data
        const products: Product[] = [
            { id: '1', name: 'Search Product 1', image: 'search1.jpg', price: 100, description: 'Description for Search Product 1', category: 'search' },
            { id: '2', name: 'Search Product 2', image: 'search2.jpg', price: 200, description: 'Description for Search Product 2', category: 'search' },
            { id: '3', name: 'Search Product 3', image: 'search3.jpg', price: 300, description: 'Description for Search Product 3', category: 'search' },
            { id: '4', name: 'Search Product 4', image: 'search4.jpg', price: 400, description: 'Description for Search Product 4', category: 'search' },
            { id: '5', name: 'Search Product 5', image: 'search5.jpg', price: 500, description: 'Description for Search Product 5', category: 'search' }
        ];
        return products;
    } catch (error) {
        console.error('Error searching products: ', error);
        return [];
    }
}