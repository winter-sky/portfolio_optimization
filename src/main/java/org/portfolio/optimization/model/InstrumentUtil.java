package org.portfolio.optimization.model;

public class InstrumentUtil {
    public static Instrument mkInstrument(
        long id,
        String name,
        InstrumentType type,
        double minimalLot,
        double[] yieldCurve,
        double[] riskCurve) {
        Instrument instr = new Instrument();

        instr.setId(id);
        instr.setName(name);
        instr.setInstrumentType(type);
        instr.setMinimalLot(minimalLot);
        instr.setYieldCurve(yieldCurve);
        instr.setRiskCurve(riskCurve);

        return instr;
    }
}
