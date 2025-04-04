export type ProductCategory = {
    id: string;
    title: string;
    description: string;
    slug: string;
};

export const getAllProductCategories = async (): Promise<ProductCategory[]> => {
    try {
        // Demo data
        const categories: ProductCategory[] = [
            { id: '1', title: 'Electronics', description: 'Devices and gadgets', slug: 'electronics' },
            { id: '2', title: 'Fashion', description: 'Clothing and accessories', slug: 'fashion' },
            { id: '3', title: 'Home Appliances', description: 'Appliances for home use', slug: 'home-appliances' },
            { id: '4', title: 'Books', description: 'Books and literature', slug: 'books' },
            { id: '5', title: 'Toys', description: 'Toys and games for kids', slug: 'toys' },
            { id: '6', title: 'Sports', description: 'Sports equipment and gear', slug: 'sports' },
            { id: '7', title: 'Beauty', description: 'Beauty and personal care products', slug: 'beauty' },
            { id: '8', title: 'Automotive', description: 'Automotive parts and accessories', slug: 'automotive' },
            { id: '9', title: 'Health', description: 'Health and wellness products', slug: 'health' },
            { id: '10', title: 'Groceries', description: 'Food and grocery items', slug: 'groceries' },
            // { id: '11', title: 'Furniture', description: 'Home and office furniture', slug: 'furniture' },
            // { id: '12', title: 'Jewelry', description: 'Jewelry and accessories', slug: 'jewelry' },
            // { id: '13', title: 'Pet Supplies', description: 'Products for pets', slug: 'pet-supplies' },
            // { id: '14', title: 'Stationery', description: 'Office and school supplies', slug: 'stationery' },
            // { id: '15', title: 'Music', description: 'Musical instruments and accessories', slug: 'music' },
            // { id: '16', title: 'Garden', description: 'Gardening tools and supplies', slug: 'garden' },
            // { id: '17', title: 'Gaming', description: 'Video games and consoles', slug: 'gaming' },
            // { id: '18', title: 'Travel', description: 'Travel accessories and gear', slug: 'travel' },
            // { id: '19', title: 'Art', description: 'Art supplies and crafts', slug: 'art' },
            // { id: '20', title: 'Baby Products', description: 'Products for babies and toddlers', slug: 'baby-products' }
        ];
        return categories;
    } catch (error) {
        console.error('Error fetching categories: ', error);
        return [];
    }
};


export const getProductCategoryBySlug = (slug: string): ProductCategory | undefined => {
    try {
        // Demo data
        const productCategory: ProductCategory = {
            id: '2',
            title: 'Fashion',
            description: 'Clothing and accessories',
            slug: 'fashion'
        };
        return productCategory;
    } catch (error) {
        console.error('Error fetching categories: ', error);
        return undefined;
    }

};
