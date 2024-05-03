package com.ecommerce.library.service;

import com.ecommerce.library.model.Voucher;

import java.util.List;

public interface VoucherService {
    Voucher addVoucher(Voucher voucher);
    void deleteVoucher(Long voucherId);
    List<Voucher> findAll();

    double applyVoucher(Voucher voucher, double totalPrice);
}
