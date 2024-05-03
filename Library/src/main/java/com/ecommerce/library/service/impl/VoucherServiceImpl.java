package com.ecommerce.library.service.impl;

import com.ecommerce.library.model.*;
import com.ecommerce.library.repository.VoucherRepository;
import com.ecommerce.library.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    public static class VoucherException extends RuntimeException {
        public VoucherException(String message) {
            super(message);
        }
    }

    public double applyVoucher(Voucher voucher, double totalPrice) {
        // Check if the voucher is valid
        if (!isVoucherValid(voucher)) {
            throw new VoucherException("Invalid voucher");
        }



        // Check if the total price meets the minimum purchase amount
        if (totalPrice < voucher.getMinPurchaseAmount()) {
            throw new VoucherException("Total price is below the minimum purchase amount");
        }

        double discountAmount = totalPrice * (voucher.getDiscountPercentage() / 100);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        discountAmount = Double.parseDouble(decimalFormat.format(discountAmount));

        // Check if the discount amount exceeds the maximum discount amount
        if (discountAmount > voucher.getMaxDiscountAmount()) {
            discountAmount = voucher.getMaxDiscountAmount();
        }


        return discountAmount;
    }

    private boolean isVoucherValid(Voucher voucher) {
        Date currentDate = new Date();
        return voucher != null && currentDate.after(voucher.getStartDate()) && currentDate.before(voucher.getEndDate());
    }


    @Override
    public Voucher addVoucher(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    @Override
    public void deleteVoucher(Long voucherId) {
        voucherRepository.deleteById(voucherId);
    }

    @Override
    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

}
