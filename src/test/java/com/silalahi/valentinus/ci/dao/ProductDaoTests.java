package com.silalahi.valentinus.ci.dao;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.silalahi.valentinus.ci.entity.Product;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Sql(scripts = { "/mysql/hapus-data.sql", "/mysql/contoh-product.sql" })
public class ProductDaoTests {
	@Autowired
	private ProductDao pd;

	@Test
	public void testSimpan() {
		Product p = new Product();
		p.setKode("A-100");
		p.setNama("Barang A 100");
		p.setHarga(new BigDecimal("1000.55"));

		Assert.assertNull(p.getId());
		pd.save(p);
		Assert.assertNotNull(p.getId());
	}

	@Test
	public void testCariById() {
		Product p = pd.findOne("xyz098");
		Assert.assertNotNull(p);
		Assert.assertEquals("A-100", p.getKode());
		Assert.assertEquals("Barang A 100", p.getNama());
		Assert.assertEquals(BigDecimal.valueOf(1000.55), p.getHarga());

		Assert.assertNull(pd.findOne("data tidak ada"));
	}
}
