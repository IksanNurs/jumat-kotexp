'use strict';
/** @type {import('sequelize-cli').Migration} */
module.exports = {
  async up(queryInterface, Sequelize) {
    await queryInterface.createTable('Reviews', {
      id: {
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
        type: Sequelize.INTEGER,
      },
      id_item: {
        type: Sequelize.INTEGER,
        allowNull: true,
        references: {
          model: 'Items',  // Assuming there's a 'Products' table with 'id' as the primary key
          key: 'id',
        },
        onUpdate: 'CASCADE',
        onDelete: 'SET NULL',
        index: true,  // index for faster querying by product_id
      },
      description: {
        type: Sequelize.TEXT,
        allowNull: true,  // nullable because it can be nil in the struct
      },
    });
  },
  async down(queryInterface, Sequelize) {
    await queryInterface.dropTable('Reviews');
  },
};
